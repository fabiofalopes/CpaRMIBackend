CD-DEISI/ULHT 1

Computação distribuída 2023/2024
Computação distribuída 2023/2024

# Projecto Concessionário Praias Algarve

1 Introdução

O objectivo do projecto de grupo com 2 ou 3 elementos é desenvolver um serviço de reserva de Sombrinhas e espreguiçadeiras em 3 praias no Algarve.

## 2 Sombrinhas e espreguiçadeiras

Na praia A existem as seguintes Sombrinhas e espreguiçadeiras:

	• 10 Sombrinhas e espreguiçadeiras para máximo 2 pessoas (A1 até A10);

	• 5 Sombrinhas e espreguiçadeiras para máximo 3 pessoas (A11 até A15);

	• 5 Sombrinhas e espreguiçadeiras para máximo 4 pessoas (A16 até A20);
	
Na praia B existem as seguintes Sombrinhas e espreguiçadeiras:

	• 5 Sombrinhas e espreguiçadeiras para máximo 2 pessoas (B1 até B5);

	• 5 Sombrinhas e espreguiçadeiras para máximo 3 pessoas (B6 até B10);

	• 1 Sombrinha e espreguiçadeiras para máximo 4 pessoas (B11);
	
Na praia C existem as seguintes Sombrinhas e espreguiçadeiras:

	• 10 Sombrinhas e espreguiçadeiras para máximo 2 pessoas (C1 até C10);
		
## 3 Arquitectura

### 3.1 Servidor Frontend
Os Clientes irão interagir com o frontend suportado por um servidor Apache Tomcat através
de 2 formas:
	• Cliente Java Webservices Rest: este cliente emprega o protocolo HTTP e acede a Web Services REST disponibilizados pelo servidor Tomcat.
	• Cliente Java Webservices SOAP: este cliente emprega mensagens XML SOAP e acede a Web Services SOAP disponibilizados pelo servidor Tomcat. 

Deve ser criada uma aplicação java cliente para interagir com cada tipo de webservice. 
O servidor frontend não tem qualquer informação das reservas, essa informação reside no servidor backend.
A parte do registo e autenticação dos utilizadores é tratada unicamente no servidor frontend.
O sistema operativo do frontend não tem qualquer restrição.
	
### 3.2 Servidor Backend
A informação das reservas está presente no servidor back-end, essa informação poderá estar em ficheiros ou suportada por uma base de dados.
A comunicação do frontend para o backend deverá ser realizada através RMI.
O sistema operativo do backend deverá ser Linux.


## 4 Operações

Deverão estar disponíveis no mínimo as seguintes operações no frontend nas 2 formas de contacto:

#### 4.1 Reservarsombrinha
A reserva é feita através da escolha da praia (A, B ou C), data e horário e do número de pessoas estando o utilizador autenticado. As reservas decorrem das 8:00 até às 20:00 por períodos de 1 hora.

#### 4.2 Cancelarsombrinha
O cancelamento da sombrinha é feito através do ID da sombrinha estando o utilizador autenticado.

#### 4.3 Listarsombrinhas
Lista as sombrinhas não reservadas numa praia, na data e hora proposta e estando o utilizador
autenticado.

#### 4.4 RegistarUtilizador
Procede ao registo de um novo utilizador utilizando o email e password.
Todas as informações das reservas terão de ser lidas e escritas no back-end. Poderá implementar novas operações que ache necessárias, justificando as mesmas no relatório.
Poderá adicionalmente como factor de valorização implementar vários servidores back-end e sincronização entre os mesmos, mantendo integridade transactional. Também serão valorizadas opções para manter segurança da autenticação e comunicação entre sistemas.

## 5 Relatório
Para além do software desenvolvido deverá ainda elaborar um relatório do trabalho, contendo os
seguintes tópicos:
• Introdução
• Arquitectura da solução
• Manual de instalação e configuração
• Manual de utilização: deve incluir um guião de demonstração com casos de utilização que
demonstram as melhores funcionalidades do trabalho.
Todas as opções não descritas no enunciado e que os elementos do grupo achem relevantes,
deverão ser explicadas e justificadas no relatório.

### 6 Entrega
A entrega deverá ser realizada até ao dia 3 de Janeiro de 2024.

### 7 Discussão
Será feita a discussão do projecto na semana que termina a 6 de Janeiro de 2024. 
Cada elemento do grupo deverá saber responder a perguntas sobre todos os componentes do projecto.