pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        sh '''javac ./java_helloWorld/src/main/java/JOAO/esii/App.java

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

    stage('Docker Container Build') {
      steps {
        sh 'sudo docker-compose build'
      }
    }

    stage('Start Containers') {
      steps {
        sh 'sudo docker-compose start'
      }
    }

  }
}