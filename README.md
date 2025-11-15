# App Overview

The app launches and displays a **loading indicator** while it fetches data from a remote **API**. Once the data is loaded, the app displays a **scrollable list** of users. At the top of the screen is a **search bar** that allows the user to filter the list.

When the user types in the **search bar**, the **user list** **filters in real-time** to show only users whose **name** or **email** contains the search query. The **search bar** also contains a **clear button** (an 'X' icon) that appears when there is text, allowing the user to quickly clear the query and see the full list again.

# Concepts Used

* **Data class** - `User` is a data class used to wireframe the data received from the API. It includes nested data classes (`Address`, `Company`) to match the JSON response from the server.
* **State Hoisting** - `UserScreen` is a stateful parent that gets the `viewModel` and collects state from it (like the `users` list and `searchQuery`). It passes this data to stateless children like `SearchBar` and `UserItemCard`, which use lambdas (like `onQueryChange`) to notify the `viewModel` to update the state.
* **ViewModel** - The `UserViewModel` is used to hold the app's business logic. It stores the complete list of users fetched from the API in memory and survives configuration changes (like screen rotation).
* **StateFlow** - State is managed in the `ViewModel` using `StateFlow`. The `UserScreen` collects these flows as state (`.collectAsState()`) to make the UI automatically react to data changes, such as the user list loading or the search query being updated.
* **Retrofit** - Used to create the network client and define the `UserApiService` interface, which makes the `suspend fun` call to the remote JSONPlaceholder API.
* **Coroutines** - `viewModelScope.launch` is used within the `ViewModel` to call the API on a background thread. This ensures the UI stays responsive and doesn't freeze while waiting for the network response.
* **Flow Operators** - The `combine` operator is used in the `ViewModel` to merge the full user list (`_apiUsers`) and the search query (`_searchQuery`). This reactively produces the final filtered `users` list that the UI displays, making the search function efficient and automatic.

<br>
<br>
<br>
<img width="328" height="734" alt="image" src="https://github.com/user-attachments/assets/4d3730d7-ca11-4349-82ff-ddb5874c32d5" />
<br>
