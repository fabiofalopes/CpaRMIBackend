# CpaBackend

## Project Description
Backend do projecto de CD. Contém o RMI Server e trata da persistencia dos dados por via de ficheiros.

- Package Server: RMI Server
- Package Client: RMI Client para testes 
- Package Reservations: Classes de dados - Ler e escrever para ficheros

## Instructions
1. Run: 
```bash
./build.sh
```
2.Run the server:
```bash
java -jar rmi_server.jar
```

3. Run the test client:
```bash
java -jar rmi_cliente.jar localhost
```

## Em docker
1. Build:
```bash
docker-compose build
```
2. Run:
```bash
docker-compose up
```

### Todo
- [ ] Afinar inicialização para prever as sombrinhas e respectivas capacidades
- [ ] Verificações na persistencia de dados. Se mantem a consistencia e estado 
  - [ ] Deve guardar para a pasta output-files. ainda nao o faz
- [ ] Criação de novas reservas. Questao do maximo de sombrinhas disponiveis
  - [ ] Fazer um enum ?? 


#### Helpers
# Java RMI
```bash
watch -n 1 'sudo netstat -pnlt | grep 1099'
```

# localhost port traffic monitoring
```bash
sudo tcpdump -i lo -n -s 0 -w - | grep -a -o -E "Host\: .*|GET \/.*"
```

# Current Project Structure

[current_project_structure.drawio](current_project_structure.drawio)