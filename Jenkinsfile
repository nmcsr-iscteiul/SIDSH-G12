def dockeruser = "nrego"
def container = "wp_g12"

node {
   echo 'Building Apache Docker Image'

stage('Git Checkout') {
    git 'https://github.com/nmcsr-iscteiul/SIDSH-G12'
    }
    
stage('Build Docker Image'){
    sh '''
        docker-compose build
    ''' 
}
    
stage ('Running Container'){
    sh '''
        docker-compose start
    ''' 
}
    
stage('Tag Docker Image'){
    sh '''
        docker tag wp_g12 nrego/wp_g12
    ''' 
}

stage('Docker Login and Push Image'){
    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'dockerpasswd', usernameVariable: 'dockeruser')]) {
    sh '''
        docker login -u ${dockeruser} -p ${dockerpasswd}
    ''' 
    }
    sh '''
        docker push ${dockeruser}/${container}"
    ''' 
    }

}