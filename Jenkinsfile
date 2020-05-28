pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        sh '''cd ./java_helloWorld/src/main/java && javac -sourcepath src -d build/classes JOAO/esii/App.java
        '''
      }
    }

    stage('Coverage / Tests') {
      steps {
        sh 'cd ./java_helloWorld/ && mvn cobertura:cobertura'
      }
    }

    stage('Javadoc') {
      steps {
        sh 'cd ./java_helloWorld/ && mvn javadoc:javadoc'
      }
    }

    stage('Generate JAR') {
      steps {
        sh '''cd ./java_helloWorld/src/main/java && echo Main-Class: JOAO.esii.App>main
        '''
        sh '''cd ./java_helloWorld/src/main/java && jar cfm build/App.jar main -C build/classes/ .
        '''
      }
    }

    stage('Stop existing Containers') {
      steps {
        sh '''



sudo docker-compose stop'''
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