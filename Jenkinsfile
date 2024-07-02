pipeline {
    agent any

    tools {
        maven 'my-maven'
    }

    environment {
        MYSQL_ROOT_LOGIN = credentials('mysql-root-login')
        AWS_ACCESS_KEY=credentials('AWS_ACCESS_KEY')
        AWS_ENDPOINT_URL=credentials('AWS_ENDPOINT_URL')
        AWS_SECRET_KEY=credentials('AWS_SECRET_KEY')
        MAIL_PASSWORD=credentials('MAIL_PASSWORD')
        MAIL_USERNAME=credentials('MAIL_USERNAME')
        RABBITMQ_HOST=credentials('RABBITMQ_HOST')
        RABBITMQ_USERNAME=credentials('RABBITMQ_USERNAME')
        RABBITMQ_PASSWORD=credentials('RABBITMQ_PASSWORD')
        RABBITMQ_PORT=credentials('RABBITMQ_PORT')
    }

    stages {
        stage('Build with Maven') {
            steps {
                sh 'mvn --version'
                sh 'java -version'
                sh 'cd source'
                sh 'cd social-networking'
                sh 'mvn clean package -Dmaven.test.failure.ignore=true'
            }
        }

        stage('Packing/Pushing Docker Image') {
            steps {
                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/') {
                    sh 'docker build -t zakushi2002/family-circle .'
                    sh 'docker push zakushi2002/family-circle'
                }
            }
        }

        stage('Deploy MySQL to DEV') {
            steps {
                echo 'Deploying and cleaning'
                sh 'docker pull mysql:8.0'
                sh 'docker network create dev || echo "Network already exists"'
                sh 'docker container stop mysql-dev || echo "Container does not exist" '
                sh 'echo y | docker container prune '
                sh 'docker volume rm mysql-dev-data || echo "Volume does not exist"'

                sh "docker run --name mysql-dev --rm --network dev -v mysql-dev-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=2002 -e MYSQL_DATABASE=family_circle  -d mysql:8.0 "
                sh 'sleep 15'
                sh "docker exec -i mysql-dev mysql --user=root --password=2002 < script"
            }
        }

        stage('Deploy Spring Boot to DEV') {
            steps {
                echo 'Deploying and cleaning'
                sh 'docker image pull zakushi2002/family-circle'
                sh 'docker container stop family-circle || echo "Container does not exist" '
                sh 'docker network create dev || echo "Network already exists"'
                sh 'echo y | docker container prune '

                sh 'docker container run -d --rm --name family-circle -p 8888:8080 --network dev zakushi2002/family-circle'
            }
        }
    }

    post {
        // Clean after build
        always {
            cleanWs()
        }
    }
}