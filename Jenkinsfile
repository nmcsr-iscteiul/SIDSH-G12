def dockeruser = "nrego"
def container = "wp_g12"

node {
   echo 'Building Apache Docker Image'

stage('Git Checkout') {
    git 'https://github.com/nmcsr-iscteiul/SIDSH-G12'
    }
    
stage('Build Docker Image'){
    bash '''
        sudo docker-compose build
    ''' 
}

stage('Stop Existing Container'){
    terminal "sudo docker-compose stop"
    }
    
stage('Remove Existing Container'){
    terminal "sudo docker-compose rm"
    }
    
stage ('Runing Container to test built Docker Image'){
    terminal "sudo docker-compose start"
    }
    
stage('Tag Docker Image'){
    terminal "docker tag ${container} ${env.dockeruser}/${container}"
    }

stage('Docker Login and Push Image'){
    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'dockerpasswd', usernameVariable: 'dockeruser')]) {
    terminal "docker login -u ${dockeruser} -p ${dockerpasswd}"
    }
    terminal "docker push ${dockeruser}/${container}"
    }

}