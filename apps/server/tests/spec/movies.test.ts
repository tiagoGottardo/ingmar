import { it, describe, expect } from "bun:test"

import { connectToServer, sendMessage, } from '../client'
import * as m from "../messages";
import { config } from '../config'

const { user, admin } = config

describe('Movie suite', async () => {
  const client = await connectToServer(config.address, config.port)

  it("should have permission to create movie", async () => {
    let res = await sendMessage(client, m.createMovie(user.token, {}))

    expect(res.status).toEqual("400")
  })

  it("should cannot create movie", async () => {
    let res = await sendMessage(client, m.createMovie(user.token, {
      titulo: "O Poderoso Chefão 3",
      diretor: "Coppola",
      ano: "1990",
      genero: ["Drama", "Ação"],
      sinopse: "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    }))

    expect(res.status).toEqual("403")
  })

  it("should cannot create movie by bad request", async () => {
    let res = await sendMessage(client, m.createMovie(admin.token, {
      titulo: "O Poderoso Chefão 1 Reboot",
      diretor: "Coppola",
      ano: "2030",
      genero: ["Máfia", "Ação"],
      sinopse: "Al pacino no espaço"
    }))

    expect(res.status).toEqual("400")
  })

  it("should cannot create movie by bad request", async () => {
    let res = await sendMessage(client, m.createMovie(admin.token, {
      titulo: "O Poderoso Chefão 1 Reboot",
      ano: "2030",
      genero: ["Ação"],
      sinopse: "Al pacino no espaço"
    }))

    expect(res.status).toEqual("400")
  })

  it("should cannot create movie by bad request", async () => {
    let res = await sendMessage(client, m.createMovie(admin.token, {
      titulo: "O",
      diretor: "Coppola",
      ano: "2030",
      genero: ["Ação"],
      sinopse: "Al pacino no espaço"
    }))

    expect(res.status).toEqual("400")
  })

  it("should cannot create movie by bad request", async () => {
    let res = await sendMessage(client, m.createMovie(admin.token, {
      titulo: "O Poderoso Chefão 1 Reboot",
      diretor: "Coppola",
      ano: "0",
      genero: ["Ação"],
      sinopse: "Al pacino no espaço"
    }))

    expect(res.status).toEqual("400")
  })

  it("should create movie", async () => {
    let res = await sendMessage(client, m.createMovie(admin.token, {
      titulo: "O Poderoso Chefão 1 Reboot",
      diretor: "Coppola",
      ano: "2030",
      genero: ["Ficção Científica", "Ação"],
      sinopse: "Al pacino no espaço"
    }))

    expect(res.status).toEqual("201")
  })

  it("should cannot update movie by bad request", async () => {
    let res = await sendMessage(client, m.editMovie(admin.token, {
      titulo: "O Poderoso Chefão 1 Reboot",
      diretor: "Coppola",
      ano: "0",
      genero: ["Ação"],
      sinopse: "Al pacino no espaço"
    }))

    expect(res.status).toEqual("400")
  })

  it("should cannot find movie to update", async () => {
    let res = await sendMessage(client, m.editMovie(admin.token, {
      id: "10000000",
      titulo: "O Poderoso Chefão 1 Reboot",
      diretor: "Coppola",
      ano: "1990",
      genero: ["Ação"],
      sinopse: "Al pacino no espaço"
    }))

    expect(res.status).toEqual("404")
  })

  it("should not have permission to update movie", async () => {
    let res = await sendMessage(client, m.editMovie(user.token, {
      id: "1",
      titulo: "O Poderoso Chefão 1 Reboot",
      diretor: "Coppola",
      ano: "1990",
      genero: ["Ação"],
      sinopse: "Al pacino no espaço"
    }))

    expect(res.status).toEqual("403")
  })

  it("should update movie", async () => {
    let res = await sendMessage(client, m.editMovie(admin.token, {
      id: "1",
      titulo: "O Poderoso Chefão 1 Reboot",
      diretor: "Coppola",
      ano: "2050",
      genero: ["Ação"],
      sinopse: "Al pacino no espaço"
    }))

    expect(res.status).toEqual("200")
  })

  it("should update movie year", async () => {
    let res = await sendMessage(client, m.editMovie(admin.token, {
      id: "1",
      ano: "2051",
    }))

    expect(res.status).toEqual("200")
  })

  it("should response movie without rating", async () => {
    let res = await sendMessage(client, m.listMovies(admin.token))

    expect(res.status).toEqual("200")
    expect(res.filmes[0].nota).toEqual("0.0")
    expect(res.filmes[0].qtd_avaliacoes).toEqual("0")
  })

})
