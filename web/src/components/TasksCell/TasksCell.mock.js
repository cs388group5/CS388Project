// Define your own mock data here:
export const standard = () => {
  return {
    //Each task has a status, a title, an Urgency value (High, Medium, or Low), and a Priority value
  tasks: [
    {status: 'Started', title: 'Task 1', urgency: "High", priority: 1},
    {status: 'Not Started', title: 'Task 2', urgency: "Medium", priority: 2},
    {status: 'Rolled Over', title: 'Task 3', urgency: "Low", priority: 3},
    {status: 'Complete', title: 'Task 4', urgency: "High", priority: 4},
    {status: 'Deleted', title: 'Task 5', urgency: "Medium", priority: 5},
  ],
}}
