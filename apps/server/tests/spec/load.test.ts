import { it, describe, expect } from "bun:test";

import { connectToServer, sendMessage } from '../client'
import { createUser } from "../messages";
import { config } from "../config";

const counter = {
  success: 0,
  failure: 0
}

const TIMES = 50;

describe.serial('Load suite', async () => {

  it(`should register ${TIMES} times`, async () => {
    const reqs = [];
    for (let i = 0; i < TIMES; i++) {
      const client = await connectToServer(config.address, config.port)

      reqs.push(sendMessage(
        client,
        createUser({
          nome: Math.floor(Math.random() * 100000).toString(),
          senha: Math.floor(Math.random() * 100000).toString()
        })
      ).then((res: any) => {
        if (res.status == "201")
          counter.success += 1;
        else
          counter.failure += 1;
      }))

    }

    await Promise.all(reqs)

    expect(counter.failure + counter.success).toEqual(TIMES)

  }, {})

})
