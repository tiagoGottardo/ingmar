import net from "net"

let client: net.Socket | null = null

export const connectToServer = (address: string, port: number): Promise<net.Socket> => {
  return new Promise((resolve, reject) => {
    if (client && !client.destroyed && client.readyState === "open")
      return resolve(client)


    client = new net.Socket()

    client.connect(port, address, () => {
      console.log(`🔌 Connecting to ${address}:${port}`)
      resolve(client!)
    })

    client.once("error", (err) => {
      console.error("Connection error:", err.message)
      reject(err)
    })

    client.once("close", () => {
      client = null
    })
  })
}

export const getClient = (): net.Socket | null => client

export const disconnectServer = (): void => {
  if (client && !client.destroyed) {
    client.end()
    client.destroy()
    client = null
  }
  console.log("🧹 Disconnecting from server")
}

export const sendMessage = (message: any): Promise<any> => {
  return new Promise((resolve, reject) => {
    const client = getClient()

    if (!client) return reject(new Error("Not connected to server."))
    
    const resolveHandler = (data: Buffer) => {
      client.removeListener("error", rejectHandler)
      resolve(JSON.parse(data.toString()))
    }

    const rejectHandler = (err: Error) => {
      client.removeListener("data", resolveHandler)
      reject(err)
    }

    client.once("data", resolveHandler)
    client.once("error", rejectHandler)

    client.write(JSON.stringify(message) + "\n")
  })
}
