import { it, describe, expect } from "bun:test"

import { connectToServer, sendMessage, } from '../client'
import { login } from "../messages";
import { config } from '../config'

const { user } = config

describe('Login suite', async () => {
  const client = await connectToServer(config.address, config.port)

  it("should login successfully", async () => {
    const res = await sendMessage(client, login(user.nome, user.senha))

    expect(res.status).toEqual("200")
    expect(res.token).toEqual(user.token)
  })

  it("should response bad request", async () => {
    const res = await sendMessage(client, login("a", "a"))

    expect(res.status).toEqual("400")
  })

  it("should response bad request", async () => {
    const res = await sendMessage(client, login("Fenerbahçe", "Fenerbahçe"))

    expect(res.status).toEqual("400")
  })

  it("should could not login ", async () => {
    const res = await sendMessage(client, login("tiaguines", "tiaguines"))

    expect(res.status).toEqual("401")
    expect(res.token).toBeEmpty()
  })

})
