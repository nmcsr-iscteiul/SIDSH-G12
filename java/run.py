from flask import Flask, render_template
from flask_restful import Resource, Api
import os

app = Flask(__name__)
api = Api(app)

# Frederico
@app.route('/covid-sci-discoveries', methods=['GET'])
def discoveries():
    os.system('./covid-sci-discoveries/run.sh')
    return render_template('covid-sci-discoveries.html')

# Bernardo
@app.route("/covid-evolution-diff", methods=['GET'])
def diff():
    os.system('./covid-evolution-diff/run.sh')
    return render_template('covid-evolution-diff.html')

# Pinto
@app.route("/covid-graph-spread", methods=['GET'])
def spread():
    os.system('./covid-graph-spread/run.sh')
    return render_template('covid-graph-spread.html')

# Tomas
@app.route("/covid-query", methods=['GET'])
def query():
    os.system('./covid-query/run.sh')
    return render_template('covid-query.html')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80, debug=True)