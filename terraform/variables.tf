variable "project" {
  type    = string
  default = "calculator"
}

variable "region" {
  type    = string
  default = "eu-north-1"
}

variable "instance_type" {
  type    = string
  default = "t3.micro"
}

variable "my_ip" {
  description = "Your public IP in CIDR form, for SSH access"
  type        = string
}

variable "public_key_path" {
  type    = string
  default = "~/.ssh/calculator-key.pub"
}

variable "ami_id" {
  description = "Pinned AMI ID for reproducible builds"
  type        = string
}
