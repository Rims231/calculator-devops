# Calculator — Full-Stack DevOps Project

A calculator application deployed to AWS using infrastructure as code.

## Stack

| Layer | Technology |
|---|---|
| Frontend | Angular 20, served by nginx |
| Backend | Java 21, Spring Boot 3.3 |
| Containers | Docker multi-stage builds, Docker Compose |
| Infrastructure | Terraform (VPC, subnet, IGW, security group, EC2) |
| Configuration | Ansible (Docker install, deployment) |
| Cloud | AWS EC2, eu-north-1 |

## Architecture

The API container is never published to the host. Only nginx is reachable
from outside; it proxies `/api` to the backend over the Docker network.
One public entry point.

## Local development

    cd calculator-api && mvn clean package && java -jar target/calculator-api.jar
    cd calculator-ui && ng serve --proxy-config proxy.conf.json

Open http://localhost:4200

## Run the containerised stack

    docker compose up -d --build
    curl http://localhost/health

## Deploy to AWS

    cd terraform
    echo "my_ip = \"$(curl -s ifconfig.me)/32\"" > terraform.tfvars
    echo 'ami_id = "ami-060423653522ff830"' >> terraform.tfvars
    terraform init && terraform plan && terraform apply

    cd ../ansible
    ansible-playbook -i inventory.ini setup.yml
    ansible-playbook -i inventory.ini deploy.yml

Tear down with `terraform destroy`.

## Notes

- AMI is pinned rather than using `most_recent = true`, which otherwise
  forces instance replacement when the vendor publishes a new image
- SSH is restricted to a single IP, not `0.0.0.0/0`
- Root volume encrypted, IMDSv2 enforced
- Health checks use `127.0.0.1` not `localhost` (containers resolve
  localhost to IPv6 first, where nginx isn't listening)
