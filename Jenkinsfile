pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        sh 'javac esii/src/main/java/JOAO/esii/App.java'
      }
    }

    stage('Coverage / Tests') {
      steps {
        sh 'cd esii && mvn cobertura:cobertura'
      }
    }

    stage('Javadoc') {
      steps {
        bat 'cd esii && mvn javadoc:javadoc'
        sh 'cd esii && mvn javadoc:javadoc'
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