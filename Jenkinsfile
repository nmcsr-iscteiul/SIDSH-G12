pipeline {
    stages {
        stage('Git Checkout') {
            git 'https://github.com/nmcsr-iscteiul/SIDSH-G12'
        }

        stage('Build Docker Container images for WP'){
            step {
                sh '''
                    sudo docker-compose stop
                '''
            }
            
        } 
        
        stage('Build Docker Container images for WP'){
            step {
                sh '''
                    sudo docker-compose build
                '''
            }
            
        } 
    }
    
}