pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Source code is available in the workspace.'
            }
        }

        stage('Clean and Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }

    post {
        always {
            junit 'course-registration-app/target/surefire-reports/TEST-*.xml'
        }

        success {
            archiveArtifacts artifacts: 'course-registration-app/target/*.jar',
                             fingerprint: true
        }

        failure {
            echo 'Pipeline failed. Check the build and test logs.'
        }
    }
}
