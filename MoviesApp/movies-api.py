import os
from flask import jsonify, request, Flask
from flaskext.mysql import MySQL

app = Flask(__name__)

mysql = MySQL()

app.config["MYSQL_DATABASE_USER"] = "root"
app.config["MYSQL_DATABASE_PASSWORD"] = os.getenv("db_root_password")
app.config["MYSQL_DATABASE_DB"] = os.getenv("db_name")
app.config["MYSQL_DATABASE_HOST"] = os.getenv("MYSQL_SERVICE_HOST")
app.config["MYSQL_DATABASE_PORT"] = int(os.getenv("MYSQL_SERVICE_PORT"))
mysql.init_app(app)


@app.route("/")
def index():
    return "Hello! This is a example app for the purposes of the project for the DevOps course!"


@app.route("/create", methods=["POST"])
def add_movie():
    json = request.json
    name = json["name"]
    director = json["director"]
    genre = json["genre"]
    if name and director and genre and request.method == "POST":
        sql = "INSERT INTO movies(name, director, genre) " "VALUES(%s, %s, %s)"
        data = (name, director, genre)
        try:
            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute(sql, data)
            conn.commit()
            cursor.close()
            conn.close()
            resp = jsonify("Movie was created successfully!")
            resp.status_code = 200
            return resp
        except Exception as exception:
            return jsonify(str(exception))
    else:
        return jsonify("Please provide correct data!")


@app.route("/movies", methods=["GET"])
def movies():
    try:
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM movies")
        rows = cursor.fetchall()
        cursor.close()
        conn.close()
        resp = jsonify(rows)
        resp.status_code = 200
        return resp
    except Exception as exception:
        return jsonify(str(exception))


@app.route("/movie/<int:movie_id>", methods=["GET"])
def movie(movie_id):
    try:
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM movies WHERE movie_id=%s", movie_id)
        row = cursor.fetchone()
        cursor.close()
        conn.close()
        resp = jsonify(row)
        resp.status_code = 200
        return resp
    except Exception as exception:
        return jsonify(str(exception))


@app.route("/update", methods=["POST"])
def update_movie():
    json = request.json
    name = json["name"]
    director = json["director"]
    genre = json["genre"]
    movie_id = json["movie_id"]
    if name and director and genre and movie_id and request.method == "POST":
        sql = "UPDATE movies SET name=%s, director=%s, " "genre=%s WHERE movie_id=%s"
        data = (name, director, genre, movie_id)
        try:
            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute(sql, data)
            conn.commit()
            resp = jsonify("Movie updated successfully!")
            resp.status_code = 200
            cursor.close()
            conn.close()
            return resp
        except Exception as exception:
            return jsonify(str(exception))
    else:
        return jsonify("Please provide id, name, director and genre")


@app.route("/delete/<int:movie_id>")
def delete_movie(movie_id):
    try:
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("DELETE FROM movies WHERE movie_id=%s", movie_id)
        conn.commit()
        cursor.close()
        conn.close()
        resp = jsonify("Movie deleted successfully!")
        resp.status_code = 200
        return resp
    except Exception as exception:
        return jsonify(str(exception))


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
