Stream.of(
Block.box(0, 0, 11, 16, 10.1, 16),
Block.box(0, 0, 5, 16, 12.6, 11),
Block.box(0, 0, 0, 16, 16, 5)
).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();