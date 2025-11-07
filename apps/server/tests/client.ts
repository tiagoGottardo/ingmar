import net from "net"

export const connectToServer = (address: string, port: number): Promise<net.Socket> => {
  return new Promise((resolve, reject) => {
    const client = new net.Socket()

    client.connect(port, address, () => resolve(client))

    client.once("error", (err) => {
      console.error("Connection error:", err.message)
      reject(err)
    })

  })
}

export const disconnectServer = (client: net.Socket): void => {
  if (client && !client.destroyed) {
    client.end()
    client.destroy()
  }
}

export const sendMessage = (client: net.Socket, message: any): Promise<any> => {
  return new Promise((resolve, reject) => {
    if (!client || client.destroyed)
      return reject(new Error("Not connected to server."))

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
