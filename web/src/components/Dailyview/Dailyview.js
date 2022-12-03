//import chakra heading
import { Heading } from '@chakra-ui/react'
//import textarea - stickynote
import { Textarea } from '@chakra-ui/react'

//function for date and time display
function callDateTime() {
  var currentDate = new Date().toDateString()
  var currentTime = new Date().toLocaleTimeString()
  document.getElementById('watch').innerHTML = `${currentDate}-${currentTime}`
}
setInterval(function () {
  callDateTime()
}, 1000)

//stickynote

//displays to screen
const Dailyview = () => {
  return (
    <div>
      <Heading>
        <p id="watch"></p>
      </Heading>
      <p>{'Start the day off right!'}</p>
      <Textarea placeholder="notes" />
    </div>
  )
}

export default Dailyview