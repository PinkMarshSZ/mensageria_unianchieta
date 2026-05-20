# 📦 Sistema de Mensageria

Projeto desenvolvido como Trabalho de Conclusão de Curso (TCC) do curso de Análise e Desenvolvimento de Sistemas.

---

## 🚀 Tecnologias Utilizadas

- Docker
- Oracle Database XE
- Visual Studio Code
- Mensageria (RabbitMQ)
- Oracle SQL

---

## 📋 Pré-requisitos

Antes de executar o projeto, instale os seguintes programas:

| Ferramenta | Download |
|---|---|
| Docker Desktop | https://docs.docker.com/desktop/setup/install/windows-install/ |
| Oracle Database XE | https://www.oracle.com/br/database/technologies/xe-downloads.html |
| Visual Studio Code | https://code.visualstudio.com/download |
| RabbitMQ | localhost:15672/#/ | (Caso queira acompanhar a fila)

---

## 🗄️ Banco de Dados

Os scripts de banco de dados e inserts estão disponíveis em:

```bash
integrador-estoque/database
```

### ⚠️ Configuração Obrigatória

O Oracle Database deve utilizar a porta:

```bash
1522
```

A porta `1522` foi definida para evitar conflitos com a porta padrão `1521`, normalmente utilizada pelo Docker e outros serviços do sistema operacional.

---

## ⚙️ Configuração do Docker

No arquivo:

```bash
docker-compose.yml
```

Configure corretamente:

- Usuário do banco Oracle;
- Senha do banco Oracle.

---

## ▶️ Como Executar o Projeto

### 1️⃣ Clone o repositório

```bash
git clone <URL_DO_REPOSITORIO>
```

---

### 2️⃣ Acesse a pasta do projeto

```bash
cd integrador-estoque
```

---

### 3️⃣ Inicie os containers Docker

```bash
docker-compose up -d
```

---

### 4️⃣ Configure o banco Oracle

- Verifique se o Oracle está executando na porta `1522`;
- Execute os scripts SQL disponíveis na pasta:

```bash
database
```

---

## 📤 Como Enviar um Pedido

Para enviar um pedido para a mensageria:

1. Abra o terminal do VS Code;
2. Localize o arquivo:

```bash
Executar_pedido_VSCODE_terminal.txt
```

3. Copie o conteúdo do arquivo;
4. Cole no terminal do VS Code;
5. Execute o comando.

O pedido será enviado automaticamente para a mensageria.

---

## 📩 Suporte

Em caso de dúvidas, entre em contato.

---

## 👨‍💻 Autor

Projeto desenvolvido para o Trabalho de Conclusão de Curso (TCC) de Análise e Desenvolvimento de Sistemas.
