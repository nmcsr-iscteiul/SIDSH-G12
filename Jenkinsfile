pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        bat 'javac esii\\src\\main\\java\\JOAO\\esii\\App.java'
      }
    }

    stage('Clean') {
      steps {
        bat 'cd esii && mvn clean'
      }
    }

    stage('Coverage / Tests') {
      steps {
        bat 'cd esii && mvn cobertura:cobertura'
      }
    }

    stage('Javadoc') {
      steps {
        bat 'cd esii && mvn javadoc:javadoc'
      }
    }

  }
}