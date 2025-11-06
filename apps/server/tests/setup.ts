import { beforeAll, afterAll } from "bun:test"
import { connectToServer, disconnectServer } from "./client"
import { config } from "./config"

beforeAll(async () => {
  const { address, port } = config
  await connectToServer(address, port)
})

afterAll(async () => {
  // await new Promise(r => setTimeout(r, 200))
  disconnectServer()
})
