// import chisel3._

// class SimpleALU extends Module {
//   val io = IO(new Bundle {
//     val operandA = Input(UInt(4.W))
//     val operandB = Input(UInt(4.W))
//     val operation = Input(UInt(4.W))
//     val result = Output(UInt(4.W))
//   })

//   // ALU operations
//   val addResult = io.operandA + io.operandB
//   val subResult = io.operandA - io.operandB
//   val andResult = io.operandA & io.operandB
//   val orResult = io.operandA | io.operandB
//   val xorResult = io.operandA ^ io.operandB

//   // Output selection based on the operation input
//   io.result := MuxLookup(io.operation, 0.U,
//     Array(
//       0.U  -> addResult,
//       1.U  -> subResult,
//       2.U  -> andResult,
//       3.U  -> orResult,
//       4.U  -> xorResult
//     )
//   )
// }

// object SimpleALU extends App {
//   chisel3.Driver.execute(args, () => new SimpleALU)
// }
import chisel3._

class SimpleALU extends Module {
  val io = IO(new Bundle {
    val operandA = Input(UInt(4.W))
    val operandB = Input(UInt(4.W))
    val operation = Input(UInt(3.W))
    val result = Output(UInt(4.W))
  })

  io.result := 0.U

  switch(io.operation) {
    is(0.U) { io.result := io.operandA + io.operandB }
    is(1.U) { io.result := io.operandA - io.operandB }
    is(2.U) { io.result := io.operandA & io.operandB }
    is(3.U) { io.result := io.operandA | io.operandB }
    is(4.U) { io.result := io.operandA ^ io.operandB }
    is(5.U) { io.result := io.operandA << 1 }
    is(6.U) { io.result := io.operandA >> 1 }
  }
}

object SimpleALU extends App {
  chisel3.Driver.execute(args, () => new SimpleALU)
}
