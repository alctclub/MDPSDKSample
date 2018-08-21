pipeline {    
    environment {        
        release_branch = "${env.release_branch}"             
        release_version = "${env.release_version}"  
        app_publish_host = "61.152.132.245"
    }
    agent any    
    stages {    
        stage('SCM SDK') {
            steps {
                git([url: 'https://github.com/alctclub/MDPSDKSample.git', branch: release_branch])
            }                            
        }        
  
        stage('Build App') {
            steps {                          
                sh "./build-app.sh"
            }
        }                   

        stage("Publish SdkDemo") {
            steps {
                sh "ssh ubuntu@${app_publish_host} 'mkdir -p /home/ubuntu/www/driver-sdk/${release_version}'" 
                sh "scp sdkSample/build/outputs/apk/qa/release/demo-qa-release.apk ubuntu@${app_publish_host}:/home/ubuntu/www/driver-sdk/${release_version}/sdkSample-qa-release.apk"                
                sh "scp sdkSample/build/outputs/apk/staging/release/demo-staging-release.apk ubuntu@${app_publish_host}:/home/ubuntu/www/driver-sdk/${release_version}/sdkSample-staging-release.apk" 
                sh "scp sdkSample/build/outputs/apk/product/release/demo-product-release.apk ubuntu@${app_publish_host}:/home/ubuntu/www/driver-sdk/${release_version}/sdkSample-product-release.apk"      
            }
        }
    }    
}    