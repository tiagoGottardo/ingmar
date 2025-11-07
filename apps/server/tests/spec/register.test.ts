import { it, describe, expect } from "bun:test";

import { sendMessage, connectToServer } from '../client'
import { config } from '../config'
import { login, createUser, listOwnUser } from "../messages";

describe.serial('Register suite', async () => {
  const client = await connectToServer(config.address, config.port)

  it("should register and login successfully", async () => {

    let res = await sendMessage(
      client,
      createUser({
        nome: "strauss",
        senha: "strauss2025"
      })
    )

    expect(res.status).toEqual("201")

    res = await sendMessage(client, login("strauss", "strauss2025"))

    expect(res.status).toEqual("200")
    expect(res.token).toBeString()

    res = await sendMessage(client, listOwnUser(res.token))

    expect(res.status).toEqual("200")
    expect(res.usuario).toEqual("strauss")
  })

  it("could not register", async () => {
    const res = await sendMessage(
      client,
      createUser({
        nome: "user",
        senha: "every"
      })
    )

    expect(res.status).toEqual("409")
  })

})
