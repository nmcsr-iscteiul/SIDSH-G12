pipeline {
  agent any
  stages {

    stage('Compile Java Programs') {
      steps {
        sh '''cd ./java/covid-evolution-diff && mvn compile
        '''
        sh '''cd ./java/covid-graph-spread ../ && mvn compile
        '''
        sh '''cd ./java/covid-sci-discoveries ../ && mvn compile
        '''
        //sh '''cd ./java/covid-query ../ && mvn compile
        //'''
      }
    }

    stage('Coverage / Tests') {
      steps {
        //sh '''cd ./java/covid-evolution-diff mvn cobertura:cobertura
        //'''
        //sh '''cd ./java/covid-graph-spread ../ && mvn cobertura:cobertura
        //'''
        sh '''cd ./java/covid-sci-discoveries ../ && mvn test
        '''
        //sh '''cd ./java/covid-query ../ && mvn cobertura:cobertura
        //'''
      }
    }

    stage('Javadoc') {
      steps {
        //sh '''cd ./java/covid-evolution-diff && mvn javadoc:javadoc
        //'''
        //sh '''cd ./java/covid-graph-spread ../ && mvn javadoc:javadoc
        //'''
        sh '''cd ./java/covid-sci-discoveries ../ && mvn javadoc:javadoc
        '''
        //sh '''cd ./java/covid-query ../ && mvn compile
        //'''
      }
    }

    stage('Stop existing Containers') {
      steps {
        sh 'sudo docker-compose down'
      }
    }

    stage('Docker Container Build') {
      steps {
        sh 'sudo docker-compose build'
      }
    }

    stage('Start Containers') {
      steps {
        sh 'sudo docker-compose up -d'
      }
    }

  }
}