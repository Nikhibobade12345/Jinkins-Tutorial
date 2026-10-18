
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
        always {
            junit(
                testResults: 'target/surefire-reports/TEST-*.xml',
                allowEmptyResults: true
            )
        }

        success {
            echo 'SUCCESS: Build, tests and report publishing completed!'
        }

        failure {
            echo 'FAILED: Check Console Output and the available test reports.'
        }
    }
}
