def dockeruser = "nrego"
def container = "wp_g12"

node {
   echo 'Building Apache Docker Image'

stage('Git Checkout') {
    git 'https://github.com/nmcsr-iscteiul/SIDSH-G12'
    }
    
stage('Build Docker Image'){
    bash "sudo docker-compose build"
    }
    
stage('Stop Existing Container'){
    bash "sudo docker-compose stop"
    }
    
stage('Remove Existing Container'){
    bash "sudo docker-compose rm"
    }
    
stage ('Runing Container to test built Docker Image'){
    bash "sudo docker-compose start"
    }
    
stage('Tag Docker Image'){
    bash "docker tag ${container} ${env.dockeruser}/${container}"
    }

stage('Docker Login and Push Image'){
    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'dockerpasswd', usernameVariable: 'dockeruser')]) {
    bash "docker login -u ${dockeruser} -p ${dockerpasswd}"
    }
    bash "docker push ${dockeruser}/${container}"
    }

}