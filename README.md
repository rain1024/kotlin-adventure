# Concurrent

Trong lập trình hiện đại, **concurrent programming** *(lập trình đồng thời)* là một khía cạnh quan trọng để tối ưu hiệu suất ứng dụng. **Kotlin** cung cấp nhiều công cụ mạnh mẽ để xử lý các tác vụ đồng thời, từ *threading* truyền thống đến các API hiện đại như **Flow** và **Channel**. Với các công cụ này, chúng ta có thể dễ dàng thực hiện các tác vụ song song, tối ưu hóa việc sử dụng tài nguyên hệ thống và cải thiện trải nghiệm người dùng.

Tuy nhiên, việc quản lý các tác vụ đồng thời thường phức tạp và dễ gặp các vấn đề như *race conditions*, *deadlocks*, hoặc *memory leaks*. Đó là lý do tại sao Kotlin giới thiệu một giải pháp ưu việt: **Coroutines** - một framework nhẹ nhàng cho lập trình bất đồng bộ.

## Coroutine

**Coroutine** là một khái niệm trong lập trình cho phép tạm dừng và tiếp tục thực thi code một cách linh hoạt. Trong Kotlin, coroutine là một framework nhẹ để xử lý các tác vụ bất đồng bộ với cú pháp tuần tự, dễ đọc. Coroutine giúp:

- Viết code bất đồng bộ với cú pháp đơn giản như code đồng bộ
- Tối ưu việc sử dụng tài nguyên bằng cách không chặn thread
- Dễ dàng xử lý nhiều tác vụ đồng thời
- Hỗ trợ hủy bỏ và xử lý lỗi một cách có cấu trúc
- Tích hợp tốt với các thành phần khác của Kotlin như Flow và Channel

**Ví dụ đơn giản về coroutine**

```Kotlin
fun main() = runBlocking {
  launch {
    delay(1000)
    println("Coroutine!")
  }
  println("Hello ")
}
```

Đoạn code trên minh họa một ví dụ đơn giản về cách sử dụng coroutine trong Kotlin:

1. `runBlocking`: Là một coroutine builder, tạo ra một coroutine scope và chặn thread hiện tại cho đến khi tất cả các coroutine con bên trong hoàn thành.

2. `launch`: Là một coroutine builder khác, tạo ra một coroutine mới và chạy nó một cách bất đồng bộ. Coroutine này sẽ chạy song song với code bên ngoài.

3. `delay(1000)`: Tạm dừng coroutine trong 1 giây (1000 milliseconds) mà không chặn thread.

Khi chạy đoạn code này:
- Đầu tiên, "Hello" sẽ được in ra ngay lập tức
- Sau đó, coroutine được tạo bởi `launch` sẽ đợi 1 giây
- Cuối cùng, "Coroutine!" sẽ được in ra

Output sẽ là:

```
Hello
Coroutine!
```

### 🔄 suspend function

**Suspend function** là một tính năng quan trọng trong Kotlin Coroutines. Đây là các hàm có thể tạm dừng thực thi mà không chặn thread, cho phép các tác vụ khác có thể chạy trong thời gian đó.

**Đặc điểm chính**

- 🔖 Được đánh dấu bằng từ khóa `suspend`
- 🔄 Chỉ có thể được gọi từ trong một coroutine hoặc một suspend function khác
- 🛠️ Có thể sử dụng các hàm suspend khác như `delay()` hoặc các hàm suspend tùy chỉnh
- 🔓 Khi tạm dừng, nó giải phóng thread cho các tác vụ khác sử dụng
- ⚙️ Tự động quản lý việc chuyển đổi context và tiếp tục thực thi sau khi tạm dừng



Ví dụ về cách sử dụng suspend function:

```Kotlin
suspend fun networkRequest(): String {
  println("Network request...")
  delay(1000)
  println("Network request finished")
  return "My Data"
}

fun showData(data: String){
  println("Data loaded: $data")
}

suspend fun loadData(){
  val data = networkRequest()
  showData(data)
}

fun main() = runBlocking {
  println("Starting...")
  loadData()
  println("Finished!")
}
```


