# Iteration 2 Worksheet

## **Paying off technical debt**

### Database function implementation:
We added Linq and Query classes in an old SDKL version that we thought we could in iteration 2 making our future work more easier. Since updating the sdk, we ended up refactoring the Linq and Queue code to match the updated SDK.

#### Link to commits that handled this technical debt pay off:
1. [Remove Unused LINQ Methods](https://code.cs.umanitoba.ca/3350-summer2023/to-be-determined/-/blob/6d886f87f441fc517839750e9ddc24a10010fd48/app/src/main/java/comp3350/inventracker/utils/LINQ.java)

2. [Remove Unused Query Methods](https://code.cs.umanitoba.ca/3350-summer2023/to-be-determined/-/blob/6d886f87f441fc517839750e9ddc24a10010fd48/app/src/main/java/comp3350/inventracker/utils/Query.java) 

We would clasify this technical debt as deliberate and inadvertent because of a lack of unforseen problems 

### Unit tests (zero coverage in iteration 1):
Did not precheck the unit test coverage before submission which increased the amount of tests we had to write for iteration 2.

#### Links to commits / files that address this technical debt pay off:
1. [[KA/add-missing-tests]: Added unit tests so we back to 100 percent method coverage](https://code.cs.umanitoba.ca/3350-summer2023/to-be-determined/-/commit/11b062d218d49acd0c03e585dbfa19d8145d96f3)

2. [Categories Manager Test](https://code.cs.umanitoba.ca/3350-summer2023/to-be-determined/-/blob/11b062d218d49acd0c03e585dbfa19d8145d96f3/app/src/test/java/comp3350/inventracker/tests/logic/CategoriesManagerTest.java)

3. [InventoryLogisticsManagerTest](https://code.cs.umanitoba.ca/3350-summer2023/to-be-determined/-/blob/11b062d218d49acd0c03e585dbfa19d8145d96f3/app/src/test/java/comp3350/inventracker/tests/logic/InventoryLogisticsManagerTest.java) 

4. [UserLoginManagerTest](https://code.cs.umanitoba.ca/3350-summer2023/to-be-determined/-/blob/11b062d218d49acd0c03e585dbfa19d8145d96f3/app/src/test/java/comp3350/inventracker/tests/logic/UserLoginManagerTest.java)

We would classify this debt as reckless and inadvertent. This is because we thought we had full coverage but did nor check the coverage through android studio. It also indicated that there test cases that were not taken into consideration.

## **Solid**

- [Group 10: Dependency Inversion Principle](https://code.cs.umanitoba.ca/3350-summer2023/segmentationfault/-/issues/79)
- [File](https://code.cs.umanitoba.ca/3350-summer2023/segmentationfault/-/blob/a838a1614d0b1eb05132a64da3ef1ae42762582b/app/src/main/java/com/erent/presentation/LoginActivity.java)

They are instantiating a concrete logic class in the ui layer, therefore not having layers of abstraction as the ui is dependent on the implementation and therefore highly coupled.

## **Retrospective**
In iteration two, we made several improvements compared to iteration one. Firstly, we started the project earlier, giving us more time to work on it. Secondly, we designed unit tests as each section was completed, ensuring better test coverage throughout the project. Lastly, we updated the progress on each task as they were completed. Additionally, during the two-week break, we utilized the time effectively by assigning tasks early, allowing each team member ample time to learn new concepts and incorporate them into the project. These changes helped us streamline our workflow and enhance the overall quality of our work in comparison to the first iteration.

## **Design Patterns**

### **Builder**
- `ProductModelQuantityDto`
- `CompleteItemDto`
- `InventoryItemDto`

These classes are composite classes of RowModel classes. They use a builder pattern to add each of these references one at a time.
 
### **Façade**
- `Services.get(Class<T>)`

`Services.get(Class<T>)` is a **façade** for all our factory methods used to create our various logic layer services.

The method search for a suitable method defined within itself that returns something assignable to the requested interface, and which takes 0 parameters. It then calls this method to supply the service.

The Services class can be considered as a **facade design pattern** because it provides a simplified interface for accessing various logic manager objects in the application.

This simplifies the client code's interaction with the logic managers by hiding the complexity of object instantiation and configuration.The get() method uses reflection to dynamically locate and invoke the appropriate factory method for the requested manager class.

This approach allows for flexibility and extensibility, as the get method will never need to change. Simply add a new factory method to have access to a new service.

### **Adapter**
- `InventoryLogisticsManager`

This class acts as a simple wrapper that adapts `InventoryLogisticsDao` to `IInventoryLogisticsManager` so that the UI is not dependent on anything in the Data Access Layer. It encapsulates the lower-level data access details and provides a higher-level interface for the logic layer to interact with.

## **Iteration 1 fixes**

### Bug reported by the grader about low sdk
- https://code.cs.umanitoba.ca/3350-summer2023/to-be-determined/-/issues/132

The reported bug was that the sdk version that was in our gradle project was too low. This would limit the version of android that our applciation would work on to Android 6.0 and lower only. To fix this we followed the instructions laid out by the grader which was to go the local.properties file in our android sdk location and the update the SDK version to 33 from 23.

## File of Commit that fixed the updated sdk bug
- https://code.cs.umanitoba.ca/3350-summer2023/to-be-determined/-/blob/16f04f79f80a7554ebac30af373ea678e4c8badd/app/build.gradle
