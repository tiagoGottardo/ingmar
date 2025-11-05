import { it, beforeAll, afterAll, describe, expect } from "bun:test";

import { connectToServer, sendMessage, disconnectServer } from '../client'
import { login, createUser, deleteOwnUser } from "../messages";
import { config } from '../config'

beforeAll(async () => {
  const { address, port } = config
  await connectToServer(address, port)
})

afterAll(() => { disconnectServer() });

describe('Register suite', () => {

  it("should register and login successfully", async () => {
    let res = await sendMessage(
      createUser({
        nome: "strauss",
        senha: "strauss2025"
      })
    )

    expect(res.status).toEqual("201")

    res = await sendMessage(login("strauss", "strauss2025"))

    expect(res.status).toEqual("200")
    expect(res.token).toBeString()

    res = await sendMessage(deleteOwnUser(res.token))

    expect(res.status).toEqual("200")
    expect(res.token).toBeEmpty()
  })

})
