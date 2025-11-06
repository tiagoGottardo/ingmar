import { it, describe, expect } from "bun:test";

import { sendMessage } from '../client'
import { login, createUser, listOwnUser } from "../messages";

describe.serial('Register suite', async () => {

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

    res = await sendMessage(listOwnUser(res.token))

    expect(res.status).toEqual("200")
    expect(res.usuario).toEqual("strauss")
  })

  it("could not register", async () => {
    const res = await sendMessage(
      createUser({
        nome: "user",
        senha: "every"
      })
    )

    expect(res.status).toEqual("409")
  })

})
