Stream.of(
Block.box(7, 0, 7, 9, 9, 9),
Block.box(5, 9, 4, 6, 13, 12),
Block.box(10, 9, 4, 11, 13, 12),
Block.box(5, 13, 4, 11, 15, 12),
Block.box(6, 9, 4, 10, 10, 12),
Block.box(6, 10, 11, 10, 13, 12)
).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();