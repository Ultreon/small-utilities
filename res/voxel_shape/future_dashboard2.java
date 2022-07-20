Stream.of(
Block.box(0, 0, 0, 5, 10.1, 16),
Block.box(5, 0, 0, 11, 12.6, 16),
Block.box(11, 0, 0, 16, 16, 16)
).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();