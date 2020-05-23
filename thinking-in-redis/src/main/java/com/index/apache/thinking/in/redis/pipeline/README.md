测试三种方式执行 set string 操作


- 循环 set
    + 当数据量在 100 时，与 pipeline 持平，随后逐渐落后于 pipeline
    + 随着数据量增大，耗时成指数级增长
    + 多次调用时，使用 mset 或 pipeline 进行调优
- mset
    + 稳定，同等 value 长度的情况下，数据量对其几乎无影响
    + 当 value 较短时，性能有绝对优势，最好
    + 当 value 逐渐增大时，性能逐渐降低，超过1k时，性能低于 set
- pipeline
    + pipeline 整体表现不错
    + 数据量增长对耗时影响较小
    + 当数据极小时，不如 set 和 mset 性能好
    + 优势在于，可使用不同操作命令，而非单一的 set 或 mset
