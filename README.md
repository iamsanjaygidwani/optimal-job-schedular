### Assumptions

- **Single-threaded cooking**: Only one pizza is cooked at a time. Once started, it runs to completion.
- **Waiting time**: Defined as `(serveTime - arrivalTime)`.
- **Input size**: Can be large (up to 10^5), so performance must be **O(n log n)**.
- **Arrival times can be sparse**: Time may need to **jump forward** if no order is present at the current time.
- **No knowledge of future arrivals**: The cook can only select from orders that have already arrived (i.e., at or before current time).
  > This means the system cannot delay execution hoping a better job will arrive later, only present information is used for decision-making.