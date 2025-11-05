import net from "net"

let client: net.Socket | null = null

export const connectToServer = (address: string, port: number): Promise<void> => {
  return new Promise((resolve, reject) => {
    client = new net.Socket()

    client.connect(port, address, () => {
      console.log(`Connected to ${address}:${port}`)
      resolve()
    })

    client.on("error", (err) => {
      console.error("Connection error:", err.message)
      reject(err)
    })

    client.on("close", () => { client = null })
  })
}

export const disconnectServer = () => { if (client) client.end() }

export const sendMessage = (message: any): Promise<any> => {
  return new Promise((resolve, reject) => {
    if (!client) return reject(new Error("Not connected to server."))

    client.once("data", (data) => {
      resolve(JSON.parse(data.toString()))
    })

    client.once("error", (err) => {
      reject(err)
    })

    client.write(JSON.stringify(message) + "\n")
  })
}
