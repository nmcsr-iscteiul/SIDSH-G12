from flask import Flask, render_template
from flask_restful import Resource, Api
import os

app = Flask(__name__)
api = Api(app)

# Frederico
@app.route("/covid-sci-discoveries")
def get():
    os.system('./covid-sci-discoveries/run.sh')
    return render_template('covid-sci-discoveries.html')




if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80, debug=True)