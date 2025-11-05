import { it, beforeAll, afterAll, describe, expect } from "bun:test"

import { connectToServer, sendMessage, disconnectServer } from '../client'
import { login } from "../messages";
import { config } from '../config'

const { user } = config

beforeAll(async () => {
  const { address, port } = config
  await connectToServer(address, port)
})

afterAll(() => { disconnectServer() });

describe('Login suite', () => {

  it("should login successfully", async () => {
    const res = await sendMessage(login(user.nome, user.senha))

    expect(res.status).toEqual("200")
    expect(res.token).toEqual(user.token)
  })

  it("should response bad request", async () => {
    const res = await sendMessage(login("a", "a"))

    expect(res.status).toEqual("400")
  })

  it("should response bad request", async () => {
    const res = await sendMessage(login("Fenerbahçe", "Fenerbahçe"))

    expect(res.status).toEqual("400")
  })

  it("should could not login ", async () => {
    const res = await sendMessage(login("tiaguines", "tiaguines"))

    expect(res.status).toEqual("401")
    expect(res.token).toBeEmpty()
  })

})
