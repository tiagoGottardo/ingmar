import { it, describe, expect } from "bun:test"

import { connectToServer, sendMessage, } from '../client'
import * as m from "../messages";
import { config } from '../config'

const { user, admin } = config

describe('Story suite', async () => {
  const client = await connectToServer(config.address, config.port)

  it("should complete user story", async () => {
    let res = await sendMessage(client, m.login(user.nome, user.senha))

    expect(res.status).toEqual("200")
    expect(res.token).toEqual(user.token)

    res = await sendMessage(client, m.listOwnUser(user.token))

    expect(res.status).toEqual("200")
    expect(res.usuario).toEqual(user.nome)

    res = await sendMessage(client, m.adminDeleteUser(user.token, "1"))
    expect(res.status).toEqual("403")

    res = await sendMessage(client, m.createMovie(user.token, {}))
    expect(res.status).toEqual("400")

    res = await sendMessage(client, m.listMovies(user.token))

    expect(res.status).toEqual("200")
    expect(res.filmes.length).toBeGreaterThan(19)
  })

  it("should complete admin story", async () => {
    let res = await sendMessage(client, m.login(admin.nome, admin.senha))

    expect(res.status).toEqual("200")
    expect(res.token).toEqual(admin.token)

    res = await sendMessage(client, m.listOwnUser(admin.token))

    expect(res.status).toEqual("200")
    expect(res.usuario).toEqual(admin.nome)

    res = await sendMessage(client, m.createUser({
      nome: "tiagones",
      senha: "tiagones"
    }))

    expect(res.status).toEqual("201")

    res = await sendMessage(client, m.listUsers(admin.token))

    expect(res.status).toEqual("200")
    expect(res.usuarios.length).toBeGreaterThan(2)

    res = await sendMessage(client, m.createMovie(admin.token, {
      titulo: "O Poderoso Chefão 3",
      diretor: "Coppola",
      ano: "1990",
      genero: ["Drama", "Ação"],
      sinopse: "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    }))

    expect(res.status).toEqual("201")

    res = await sendMessage(client, m.listMovies(admin.token))

    expect(res.status).toEqual("200")
    expect(res.filmes.length).toBeGreaterThan(20)
  })

})
