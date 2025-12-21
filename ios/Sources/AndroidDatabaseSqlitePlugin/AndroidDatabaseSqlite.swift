import Foundation

@objc public class AndroidDatabaseSqlite: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
