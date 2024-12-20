from flask import Flask, jsonify, request
from mysql.connector import connect
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

config = {
    'host': 'mysql',
    'user': 'backend',
    'password': '12345',
    'database': '3myapp'
}

# Función para crear las tablas con el atributo 'activo'
# Función para crear las tablas con el atributo 'activo'
def create_tables():
    connection = connect(**config)
    cursor = connection.cursor()

    # Crear tabla Ubicaciones
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS Ubicaciones (
            id_ubicacion INT AUTO_INCREMENT PRIMARY KEY,
            nombre TEXT,
            activo BOOLEAN DEFAULT TRUE
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS Usuarios (
            id_usuario INT AUTO_INCREMENT PRIMARY KEY,
            nombre TEXT,
            correo VARCHAR(255) UNIQUE,
            contraseña TEXT,
            rol TEXT CHECK(rol IN ('admin', 'usuario')),
            activo BOOLEAN DEFAULT TRUE
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS Tiendas (
            id_tienda INT AUTO_INCREMENT PRIMARY KEY,
            id_ubicacion INT,
            nombre TEXT,
            activo BOOLEAN DEFAULT TRUE,
            FOREIGN KEY (id_ubicacion) REFERENCES Ubicaciones(id_ubicacion)
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS Productos (
            id_producto INT AUTO_INCREMENT PRIMARY KEY,
            id_tienda INT,
            nombre TEXT,
            identificador VARCHAR(255) UNIQUE,
            fecha_vencimiento DATE,
            cantidad INT,
            qr_code TEXT,
            activo BOOLEAN DEFAULT TRUE,
            FOREIGN KEY (id_tienda) REFERENCES Tiendas(id_tienda)
        )
    """)

    cursor.execute("""
        CREATE TABLE IF NOT EXISTS Usuario_Tiendas (
            id_usuario INT,
            id_tienda INT,
            activo BOOLEAN DEFAULT TRUE,
            PRIMARY KEY (id_usuario, id_tienda),
            FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
            FOREIGN KEY (id_tienda) REFERENCES Tiendas(id_tienda)
        )
    """)
    connection.commit()
    cursor.close()
    connection.close()

# Función para insertar datos de ejemplo con el atributo 'activo'
def insertar_datos_ejemplo():
    connection = connect(**config)
    cursor = connection.cursor()

    # Vaciar las tablas antes de insertar los datos
    cursor.execute("DELETE FROM Usuario_Tiendas")
    cursor.execute("DELETE FROM Productos")
    cursor.execute("DELETE FROM Tiendas")
    cursor.execute("DELETE FROM Usuarios")
    cursor.execute("DELETE FROM Ubicaciones")

    connection.commit()

    # Insertar ubicaciones de ejemplo
    cursor.execute("""
        INSERT INTO Ubicaciones (nombre, activo)
        VALUES
        ('Ubicación 1', TRUE),
        ('Ubicación 2', TRUE)
    """)

    # Confirmar que las ubicaciones fueron insertadas
    connection.commit()

    # Obtener los IDs de las ubicaciones recién insertadas
    cursor.execute("SELECT id_ubicacion FROM Ubicaciones WHERE nombre = 'Ubicación 1'")
    ubicacion1_id = cursor.fetchone()[0]
    
    cursor.execute("SELECT id_ubicacion FROM Ubicaciones WHERE nombre = 'Ubicación 2'")
    ubicacion2_id = cursor.fetchone()[0]

    # Insertar usuarios de ejemplo
    cursor.execute("""
        INSERT INTO Usuarios (nombre, correo, contraseña, rol, activo)
        VALUES
        ('Tristan', 'tristan@example.com', '12345', 'admin', TRUE),
        ('Diego', 'diego@example.com', '12345', 'usuario', TRUE)
    """)

    connection.commit()

    # Insertar tiendas de ejemplo, usando los IDs de ubicaciones obtenidos dinámicamente
    cursor.execute("""
        INSERT INTO Tiendas (id_ubicacion, nombre, activo)
        VALUES
        (%s, 'Tienda 1', TRUE),
        (%s, 'Tienda 2', TRUE)
    """, (ubicacion1_id, ubicacion2_id))

    cursor.execute("SELECT id_tienda FROM Tiendas WHERE nombre = 'Tienda 1'")
    tienda1_id = cursor.fetchone()[0]

    cursor.execute("SELECT id_tienda FROM Tiendas WHERE nombre = 'Tienda 2'")
    tienda2_id = cursor.fetchone()[0]

    # Insertar productos de ejemplo
    cursor.execute("""
        INSERT INTO Productos (id_tienda, nombre, identificador, fecha_vencimiento, cantidad, qr_code, activo)
        VALUES
        (%s, 'Producto 1', 'ABC123', '2022-12-31', 10, 'qr_code_1', TRUE),
        (%s, 'Producto 2', 'DEF456', '2023-06-30', 20, 'qr_code_2', TRUE)
    """, (tienda1_id, tienda2_id))

    #id usuario
    cursor.execute("SELECT id_usuario FROM Usuarios WHERE correo = 'tristan@example.com'")
    id_usuario = cursor.fetchone()[0]


    # Insertar usuario-tiendas de ejemplo
    cursor.execute("""
        INSERT INTO Usuario_Tiendas (id_usuario, id_tienda, activo) 
        VALUES 
        (%s, %s, TRUE),
        (%s, %s, TRUE)
    """, (id_usuario, tienda1_id, id_usuario, tienda2_id))


    connection.commit()
    cursor.close()
    connection.close()

# Crear las tablas al iniciar la aplicación
create_tables()

# Insertar datos de ejemplo
insertar_datos_ejemplo()

# Método para login
@app.route('/login', methods=['POST'])
def login():
    try:
        data = request.get_json()
        username = data.get('username')
        password = data.get('password')

        if not username or not password:
            return jsonify({"error": "Missing username or password"}), 400

        connection = connect(**config)
        cursor = connection.cursor()
        cursor.execute("SELECT * FROM Usuarios WHERE correo = %s AND activo = TRUE", (username,))
        usuario = cursor.fetchone()
        cursor.close()
        connection.close()

        if not usuario:
            return jsonify({"error": "User not found"}), 404

        if usuario[3] != password:  # Contraseña en índice 3
            return jsonify({"error": "Incorrect password"}), 401

        return jsonify({"success": True, "user": {"id_usuario": usuario[0], "nombre": usuario[1], "correo": usuario[2]}})
    
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# Método para crear usuario
@app.route('/usuario', methods=['POST'])
def crear_usuario():
    try:
        data = request.get_json()

        connection = connect(**config)
        cursor = connection.cursor()

        cursor.execute("SELECT * FROM Usuarios WHERE correo = %s", (data['correo'],))
        existing_user = cursor.fetchone()

        if existing_user:
            return jsonify({"error": "El correo electrónico ya está registrado"}), 400

        cursor.execute("""
            INSERT INTO Usuarios (nombre, correo, contraseña, rol, activo) 
            VALUES (%s, %s, %s, %s, TRUE)
        """, (data['nombre'], data['correo'], data['contraseña'], data['rol']))
        connection.commit()

        cursor.close()
        connection.close()

        return jsonify({"success": True})
    
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# Método para obtener tiendas, ubicaciones y productos de un usuario
@app.route('/usuario_tienda_productos/<int:id_usuario>', methods=['GET'])
def obtener_tienda_con_ubicacion_y_productos(id_usuario):
    try:
        connection = connect(**config)
        cursor = connection.cursor()

        cursor.execute("""
            SELECT Tiendas.id_tienda, Tiendas.nombre AS tienda, Ubicaciones.nombre AS ubicacion
            FROM Tiendas
            JOIN Usuario_Tiendas ON Tiendas.id_tienda = Usuario_Tiendas.id_tienda
            JOIN Ubicaciones ON Tiendas.id_ubicacion = Ubicaciones.id_ubicacion
            WHERE Usuario_Tiendas.id_usuario = %s AND Usuario_Tiendas.activo = TRUE AND Tiendas.activo = TRUE AND Ubicaciones.activo = TRUE
        """, (id_usuario,))
        
        tiendas_ubicaciones = cursor.fetchall()
        resultado = []
        for tienda in tiendas_ubicaciones:
            id_tienda = tienda[0]
            nombre_tienda = tienda[1]
            ubicacion = tienda[2]
            
            cursor.execute("""
                SELECT nombre, identificador, cantidad, fecha_vencimiento 
                FROM Productos 
                WHERE id_tienda = %s AND activo = TRUE
            """, (id_tienda,))
            
            productos = cursor.fetchall()
            
            tienda_info = {
                "tienda": nombre_tienda,
                "ubicacion": ubicacion,
                "productos": [
                    {
                        "nombre": producto[0],
                        "identificador": producto[1],
                        "cantidad": producto[2],
                        "fecha_vencimiento": producto[3].strftime('%Y-%m-%d') if producto[3] else None
                    }
                    for producto in productos
                ]
            }
            resultado.append(tienda_info)

        cursor.close()
        connection.close()

        return jsonify(resultado)

    except Exception as e:
        return jsonify({"error": str(e)}), 500
    
@app.route('/desactivar_tienda/<string:nombre_tienda>', methods=['PATCH'])
def desactivar_tienda(nombre_tienda):
    try:
        connection = connect(**config)
        cursor = connection.cursor()

        # Desactivar la tienda utilizando el nombre
        cursor.execute("""
            UPDATE Tiendas 
            SET activo = FALSE 
            WHERE nombre = %s
        """, (nombre_tienda,))
        
        connection.commit()

        # Comprobar si alguna fila fue afectada (es decir, si la tienda fue encontrada y desactivada)
        if cursor.rowcount > 0:
            message = f"Tienda '{nombre_tienda}' desactivada exitosamente."
            success = True
        else:
            message = f"No se encontró una tienda con el nombre '{nombre_tienda}'."
            success = False

        cursor.close()
        connection.close()

        return jsonify({"success": success, "message": message})

    except Exception as e:
        return jsonify({"error": str(e)}), 500
    
@app.route('/actualizar_tienda/<string:nombre_tienda>', methods=['PATCH'])
def actualizar_tienda(nombre_tienda):
    try:
        # Recuperar los datos enviados en el cuerpo de la solicitud
        data = request.get_json()
        nuevo_nombre = data.get('nuevo_nombre')

        # Verificar que se haya proporcionado un nuevo nombre
        if not nuevo_nombre:
            return jsonify({"error": "Se debe proporcionar un nuevo nombre para la tienda"}), 400

        # Conectar a la base de datos
        connection = connect(**config)
        cursor = connection.cursor()

        # Actualizar el nombre de la tienda si se proporciona un nuevo nombre
        cursor.execute("""
            UPDATE Tiendas
            SET nombre = %s
            WHERE nombre = %s
        """, (nuevo_nombre, nombre_tienda))

        connection.commit()

        # Verificar si alguna fila fue afectada
        if cursor.rowcount > 0:
            message = f"Tienda '{nombre_tienda}' actualizada exitosamente a '{nuevo_nombre}'."
            success = True
        else:
            message = f"No se encontró una tienda con el nombre '{nombre_tienda}'."
            success = False

        cursor.close()
        connection.close()

        return jsonify({"success": success, "message": message})

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True, port=8080)
