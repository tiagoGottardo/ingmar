import { it, describe, expect } from "bun:test";

import { sendMessage } from '../client'
import { login, createUser, listOwnUser } from "../messages";

const counter = {
  success: 0,
  failure: 0
}

const TIMES = 70;

describe.serial('Load suite', async () => {

  it(`should register ${TIMES} times`, async () => {
    for(let i = 0; i < TIMES; i++) {
      let res = await sendMessage(
        createUser({
          nome: Math.floor(Math.random() * 100000).toString(),
          senha: Math.floor(Math.random() * 100000).toString()
        })
      )

      if(res.status == "201")
        counter.success += 1;
      else 
        counter.failure += 1;
    }

    expect(counter.failure + counter.success).toEqual(TIMES)

  })

})
