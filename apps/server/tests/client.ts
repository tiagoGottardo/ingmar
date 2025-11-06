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

    client.on("error", (err) => {
      console.error("Connection error:", err.message)
      reject(err)
    })

    client.on("close", () => {
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
    if (!getClient()) return reject(new Error("Not connected to server."))

    getClient()?.once("data", (data) => {
      resolve(JSON.parse(data.toString()))
    })

    getClient()?.once("error", (err) => {
      reject(err)
    })

    getClient()?.write(JSON.stringify(message) + "\n")
  })
}
