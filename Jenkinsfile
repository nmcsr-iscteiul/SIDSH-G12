pipeline {
  agent any
  stages {

    stage('Compile Java Programs') {
      steps {
        sh '''cd ./java/covid-evolution-diff && mvn compile
        '''
        sh '''cd ./java/covid-graph-spread && mvn compile
        '''
        sh '''cd ./java/covid-sci-discoveries && mvn compile
        '''
        sh '''cd ./java/covid-query && mvn compile
        '''
      }
    }

    stage('Coverage / Tests') {
      steps {
        sh '''cd ./java/covid-evolution-diff && mvn test
        '''
        sh '''cd ./java/covid-graph-spread && mvn test
        '''
        sh '''cd ./java/covid-sci-discoveries && mvn test
        '''
        sh '''cd ./java/covid-query && mvn test
        '''
      }
    }

    stage('Javadoc') {
      steps {
        sh '''cd ./java/covid-evolution-diff && mvn javadoc:javadoc
        '''
        sh '''cd ./java/covid-graph-spread && mvn javadoc:javadoc
        '''
        sh '''cd ./java/covid-sci-discoveries && mvn javadoc:javadoc
        '''
        sh '''cd ./java/covid-query && mvn javadoc:javadoc
        '''
      }
    }

    stage('Stop existing Containers') {
      steps {
        sh 'cd /home/nrego/Documents/Code/SIDSH-G12/ && sudo docker-compose down'
      }
    }

    stage('Start Containers') {
      steps {
        sh 'cd /home/nrego/Documents/Code/SIDSH-G12/ && sudo docker-compose up -d'
      }
    }

  }
}