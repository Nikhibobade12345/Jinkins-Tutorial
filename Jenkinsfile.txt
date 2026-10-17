
pipeline {
    agent any

    stages {
        stage('Build and Test') {
            steps {
                bat 'mvnw.cmd -B clean verify'
            }
        }

        stage('Archive JAR') {
            steps {
                archiveArtifacts(
                    artifacts: 'target/*.jar',
                    fingerprint: true
                )
            }
        }
    }

    post {
        success {
            echo 'SUCCESS: Spring Boot application built and tested!'
        }

        failure {
            echo 'FAILED: Check the Console Output for errors.'
        }
    }
}
