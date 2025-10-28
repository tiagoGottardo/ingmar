# Ingmar

## Como rodar
### Pré-requisitos
- Você deve ter o java versão 21.0.2 (funciona em outras versões mas não é garantido) como descrito no mise.toml.
- Você deve rodar uma instância de postgres como descrito no docker-compose.yaml. Caso tenha docker, apenas rode `docker compose up -d`. Caso não tenha docker, lembre-se de usar os mesmos dados do docker-compose.yaml e rodar o arquivo de init.
- Você não precisar instalar nada em relação ao gradle.

### Rodar servidor
Linux ou Mac
```sh
./gradlew :apps:server:run
```

Windows
```sh
./gradlew.bat :apps:server:run
```

Você pode escolher o endereço e a porta usando --args
```sh
./gradlew :apps:server:run --args="0.0.0.0 8080"
```

### Rodar cliente
De forma semelhante pode rodar:
```sh
./gradlew :apps:client:run
```

## Funcionalidades

- Criar usuário
- Realizar Login
- Realizar Logout
- Deletar usuário
- Atualizar nome ou senha do usuário
- Ver dados do usuário logado
- Uso de JWT
