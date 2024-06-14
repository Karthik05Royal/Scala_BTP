import chisel3._
import chisel3.util._

class SimpleProcessor extends Module {
  val io = IO(new Bundle {
    val memWrite = Input(Bool())
    val memAddress = Input(UInt(16.W))
    val memDataIn = Input(UInt(8.W))
    val memRead = Output(Bool())
    val memDataOut = Output(UInt(8.W))
  })

  // Define processor state elements
  val pc = RegInit(0.U(16.W))
  val register = Mem(16, UInt(8.W))

  // Fetch stage
  val instruction = register(pc)

  // Decode stage (simplified for illustration)
  val opcode = instruction(7, 4)
  val operand = instruction(3, 0)

  // Execute stage
  val result = MuxCase(0.U(8.W), Array(
    (opcode === "b0000".U) -> (register(operand) + 1.U),
    (opcode === "b0001".U) -> (register(operand) - 1.U),
    // Add more instructions as needed
  ))

  // Write-back stage
  when(io.memWrite) {
    register(operand) := io.memDataIn
  }

  // Memory access stage
  io.memRead := io.memWrite
  io.memDataOut := result

  // Update program counter
  pc := pc + 1.U
}

object SimpleProcessorMain extends App {
  chisel3.Driver.execute(args, () => new SimpleProcessor)
}