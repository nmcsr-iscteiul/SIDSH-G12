from flask import Flask, render_template,request
from flask_restful import Resource, Api
import os

app = Flask(__name__)
api = Api(app)

# Frederico
@app.route('/covid-sci-discoveries', methods=['GET'])
def discoveries():
    os.system('rm -rf /usr/src/app/java/covid-sci-discoveries/HTML/Covid_Scientific_Discoveries_Repository/* && cp -a /usr/src/app/web/wp-content/uploads/.pdf /usr/src/app/java/covid-sci-discoveries/HTML/Covid_Scientific_Discoveries_Repository && ls /usr/src/app/java/covid-sci-discoveries/HTML/Covid_Scientific_Discoveries_Repository/ && cp /usr/src/app/templates/header.html /usr/src/app/java/covid-sci-discoveries/HTML/ && cp /usr/src/app/templates/footer.html /usr/src/app/java/covid-sci-discoveries/HTML/ && cd /usr/src/app/java/covid-sci-discoveries/ && mvn exec:java -Dexec.mainClass=covid_sci_discoveries.covid_sci_discoveries.Main && rm -rf /usr/src/app/web/Covid_Scientific_Discoveries_Repository/ && cp -a /usr/src/app/java/covid-sci-discoveries/HTML/Covid_Scientific_Discoveries_Repository/. /usr/src/app/web/Covid_Scientific_Discoveries_Repository/ && cp -a /usr/src/app/java/covid-sci-discoveries/HTML/covid-sci-discoveries.html /usr/src/app/templates/')
    return render_template('covid-sci-discoveries.html')

# Bernardo
@app.route("/covid-evolution-diff", methods=['GET'])
def diff():
    os.system('cp /usr/src/app/templates/header.html /usr/src/app/java/covid-evolution-diff/HTML/ && cp /usr/src/app/templates/footer.html /usr/src/app/java/covid-evolution-diff/HTML/ && cd /usr/src/app/java/covid-evolution-diff/ && mvn exec:java -Dexec.mainClass=covid_evolution_diff.Main && cp -a /usr/src/app/java/covid-evolution-diff/HTML/covid-evolution-diff.html /usr/src/app/templates/')
    return render_template('covid-evolution-diff.html')

# Pinto
@app.route("/covid-graph-spread", methods=['GET'])
def spread():
    os.system('p /usr/src/app/templates/header.html /usr/src/app/java/covid-graph-spread/HTML/ && cp /usr/src/app/templates/footer.html /usr/src/app/java/covid-graph-spread/HTML/ && cd /usr/src/app/java/covid-graph-spread/ && mvn exec:java -Dexec.mainClass=main.Main && cp /usr/src/app/java/covid-graph-spread/HTML/covid-graph-spread.html /usr/src/app/templates/')
    return render_template('covid-graph-spread.html')

# Tomas
@app.route("/covid-query", methods=['POST'])
def query():
    data = "\'"+request.form['data'] + "\'"
    print (data)
    os.system("rm -rf ./java/covid-query/repCopy && cd ./java/covid-query && mvn exec:java -Dexec.mainClass=covid_query.covid_query.App -Dexec.args=\" " + request.form['data'] + "\" && cd ../../ && cp ./java/covid-query/HTML/covid-queries.html ./templates/covid-queries.html")
    return render_template('covid-queries.html')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80, debug=True)