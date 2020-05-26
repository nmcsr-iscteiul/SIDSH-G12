node {
    echo 'Building Apache Docker Container'

    stage('Git Checkout') {
        git 'https://github.com/nmcsr-iscteiul/SIDSH-G12'
    }
    
    stage('Build Docker Container images for WP'){
        step {
            sh '''
                sudo docker-compose build
            '''
        }
        
    } 
    
    stage ('Runing Container'){
        step {
            sh '''
                sudo docker-compose start
            '''
        }
    }    
}